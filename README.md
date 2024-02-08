

###### Dynamic image versioning in Kubernetes within CI/CD pipelines addresses the need for efficient and reliable application deployment. With dynamic versioning, developers can automate the process of updating container images in Kubernetes deployments, ensuring that the latest changes are seamlessly integrated into the application environment. This approach eliminates manual interventions and reduces the risk of errors associated with manual image tagging and deployment.


###### I have utilized Jenkins in my CI/CD pipeline to dynamically update Kubernetes deployment YAML files. This process ensures that our Kubernetes deployments are always up-to-date with the latest changes with the build version that is being built . By integrating Jenkins into our CI/CD workflow, we can automate the deployment process and maintain consistency across environments.

###### I’ve developed a lightweight Groovy framework containing utility functions specifically designed for use in Jenkins file. These utilities streamline various tasks within the CI/CD pipeline, enhancing automation and efficiency. Additionally, the framework supports manual approval steps during production deployments for added control and security.

Original link : https://medium.com/@wholeofmine/implementing-dynamic-docker-image-versioning-in-kubernetes-deployment-yaml-efficient-ci-cd-d21c98ccaf10


###  Notes:
To use the groovy pipeline scripts in shell/Linux, other than bat script inside the pipeline

 This project depends on https://github.com/gitmnd/groovy-framework-libs for groovy libraries for jenkinsfile to execute


Shell:
````
sh """
    docker image build  
"""
````

bat:

```

def command = """
docker image build  
"""
def process = "cmd /c ${command}".execute()

````

## ECR Repo
You need to have ECR repo's created with below

```
cicd-kubernetes-image-version-jenkins-nonprod
cicd-kubernetes-image-version-jenkins-prod
```


## After deployment , you can access below api endpoints

http://localhost:<NODEPORT>>/ping

http://localhost:<NODEPORT>>/user/7869923