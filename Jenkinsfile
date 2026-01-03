pipeline{
	agent any

	environment{
		IMAGE_NAME = "anouarbarry/SmartDelivery"
		IMAGE_TAG = "latest"
		DOCKER_CRED_ID = "docker-hub-credentials"
	}
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

		stage('SonarQube Analysis') {
			steps{
				withSonarQubeEnv('sonar-server') {
					bat 'mvn sonar:sonar'
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

		stage('Build Docker Image') {
			steps {
				script {
					echo 'Building Docker Image...'
					// Requires a 'Dockerfile' in the root of your project
					bat "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
				}
			}
		}

		stage('Push Docker Image') {
			steps{
				script{
					echo 'Pushing to Docker Hub...'
					withCredentials([usernamePassword(credentialsId: DOCKER_CRED_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
						// Login, Push, and Logout for security
						bat "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
						bat "docker push $IMAGE_NAME:$IMAGE_TAG"
						bat "docker logout"
					}
				}
			}

		}

	}
}