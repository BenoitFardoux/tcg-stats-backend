pipeline {
    agent {
        docker {
            image 'docker:24.0.6-dind'
            args '--privileged -v /var/run/docker.sock:/var/run/docker.sock --user root'
        }
    }
    environment {
        GRADLE_USER_HOME = '/tmp/gradle'
        GIT_REPO = 'https://github.com/BenoitFardoux/tcg-stats-backend.git'
        BRANCH = 'main'
    }
    stages {
        stage('Install Dependencies') {
            steps {
                sh '''
                apk add --no-cache openjdk17 curl unzip bash
                
                GRADLE_VERSION=8.4
                curl -sSL https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o gradle.zip
                unzip gradle.zip
                mv gradle-${GRADLE_VERSION} /opt/gradle
                ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle
                
                gradle -version
                '''
            }
        }

        stage('Setup MariaDB') {
            steps {
                script {
                    sh '''
                    docker network create jenkins-network || true
                    docker run -d --rm --name mariadb \
                        --network jenkins-network \
                        -e MYSQL_ROOT_PASSWORD=password \
                        -e MYSQL_DATABASE=statistiques_mtg_test \
                        -e MYSQL_USER=root \
                        -e MYSQL_PASSWORD=password \
                        -p 3306:3306 \
                        mariadb:10.5
        
                    echo "🕒 Attente que MariaDB démarre..."
                    sleep 15  # Augmente à 20 ou 30 si nécessaire
        
                    echo "✅ MariaDB devrait être prête"
                    '''
                }
            }
        }

        stage('Clone Repository') {
            steps {
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Build Project') {
            steps {
                sh 'gradle build --gradle-user-home /tmp/gradle -x test'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'gradle test --gradle-user-home /tmp/gradle'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }
    }
    
    post {
        success {
            echo '✅ Build et tests réussis !'
        }
        failure {
            echo '❌ Erreur dans le pipeline !'
        }
    }
}
