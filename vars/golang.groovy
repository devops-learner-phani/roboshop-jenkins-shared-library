def call() {
  node() {

    common.pipelineInit()
    stage('Download dependencies') {
      sh '''go get
      go build'''
    }
  }
}    