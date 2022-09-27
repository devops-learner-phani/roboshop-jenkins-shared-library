def call() {
  node() {

    common.pipelineInit()
    stage('Download dependencies') {
      sh '''go mod init dispatch
      go get
      go build'''
    }
  }
}    