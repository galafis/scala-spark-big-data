# main.tf - Infraestrutura AWS
# TODO: Configuração principal do Terraform segundo README.md

terraform {
  required_version = ">= 1.0.0"
}

provider "aws" {
  region = "us-east-1"
}

# TODO: Adicionar recursos conforme necessidade do projeto
