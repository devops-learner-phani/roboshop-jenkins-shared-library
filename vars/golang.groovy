def call() {
  node() {

    stage('Download dependencies') {
      sh '''go get
      go build'''
    }
  }
}