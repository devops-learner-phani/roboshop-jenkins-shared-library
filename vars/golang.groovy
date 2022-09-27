def call() {
  node() {
    
    stage('Download dependencies') {
      sh """
        go mod init dispatch
        go get
        go build
      """
    }
  }
}    