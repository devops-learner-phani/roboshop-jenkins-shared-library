def call() {
  node() {

    common.pipelineInit()
    stage('Download dependencies') {
      sh """
        go get
        go build
      """
    }

    if (env.BRANCH_NAME == env.TAG_NAME)
    {
      common.publishArtifacts()
    }
  }
}    