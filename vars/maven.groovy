def call() {
  node() {
    common.pipelineInit()
    stage('Download dependencies') {
      sh 'mvn clean package'
    }
  }
}