def call() {
  node() {

    stage('Download dependencies') {
      sh 'mvn clean package'
    }
  }
}