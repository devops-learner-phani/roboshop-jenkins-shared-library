def call() {
  node() {

    stage(Terraform INIT) {
      sh 'terraform init'
    }

    stage(terraform plan) {
      sh 'terraform plan'
    }

    stage(terraform Apply) {
      sh 'terraform apply -auto-approve'
    }
  }
}