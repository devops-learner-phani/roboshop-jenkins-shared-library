def call() {
  node() {

    stage('Download dependencies') {
      sh '''go mod init ${COMPONENT}
      go get
      go build'''
    }
  }
}