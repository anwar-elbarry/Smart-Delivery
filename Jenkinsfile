pipeline{
	agent any
	tools{
		maven "M3"
		jdk "JDK-21"
	}
	stages{
		stage('checkout'){
			steps{
				checkout scm
			}
		}
		stage('build') {
			steps{
				bat 'mvn clean compile'
			}

		}
        stage('test') {
			steps{
				bat 'mvn test'
			}
			post{
				always{
					junit '**/target/surefire-reports/*.xml'
				}
			}
		}

		stage('SonarQube Analysis') {
			steps{
				withSonarQubeEnv('sonar-server') {
					sh 'mvn sonar:sonar'
				}
			}
		}
		stage('Quality Gate') {
			steps{
				timeout(time: 2, unit: 'MINUTES') {
					waitForQualityGate abortPipeline: true
				}
			}
		}
		stage('package') {
			steps{
				bat 'mvn clean package -DskipTests'
			}
			post{
				success{
					archiveArtifacts 'target/*.jar, dist/**'  // Archives build outputs
				}
			}
		}

	}
}