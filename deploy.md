# DEPLOYMENT INSTRUCTIONS 

## Base info
- After changes in code when you need to deploy actual state of project
- You can find deploy script in root directory - `deploy-sandbox`

## Follow the next steps

### - Configure task of building new docker image:
        in build.gradle file you can see task that called docker.
        You can configure the registry url, tags of images and files copy process

        dockerRegistryName - variable for registry name

### - Configure the path of public key
        in deploy script you need to define path to public key 
        and set to variable PUBLIC_KEY_PATH

### - Configure the registry
        in deploy script you need to set the registry of image in this string
        e.g - ssh -i $PUBLIC_KEY_PATH ec2-user@ec2-3-66-190-217.eu-central-1.compute.amazonaws.com "docker service update --image %%registry_name/image%% eliftech_back"

### Executing
        after previous steps you need just run the script 