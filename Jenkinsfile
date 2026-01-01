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
				sh 'mvn clean compile'
			}

		}
        stage('test') {
			steps{
				sh 'mvn test'
			}
			post{
				always{
					junit '**/target/surefire-reports/*.xml'
				}
			}
		}
		stage('package') {
			steps{
				sh 'mvn clean package -DskipTests'
			}
			post{
				success{
					archiveArtifacts 'target/*.jar, dist/**'  // Archives build outputs
				}
			}
		}

	}
}