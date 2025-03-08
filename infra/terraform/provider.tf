

provider "google" {
    credentials = file(var.gcp_svc_key_path)
    project = var.gcp_project
    region = var.gcp_region
}