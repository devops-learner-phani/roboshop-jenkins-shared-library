def call() {
  node {

    ansiColor('xterm') {

      stage('Terraform INIT') {
        sh 'terraform init'
      }

      stage('Terraform plan') {
        sh 'terraform plan'
      }

      stage('terraform apply') {
        sh 'terraform apply -auto-approve'
      }
    }         
  }
}