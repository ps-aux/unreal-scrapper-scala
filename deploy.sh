#!/bin/bash
script_dir=$(dirname $0)

if [ "$#" -ne 1 ]; then
    echo "Exactly one argument is required (location of artifacts dir)"
    exit 1
fi

echo "Deploying unreal-scrapper via ansible. Artifacts dir is $1"
cd "$script_dir/deployment"
ansible-playbook "deploy.yml" --extra-vars="artifact_path=$1"


