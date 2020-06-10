FROM airhacks/glassfish
COPY ./target/JakartaEEWebApplicationExample.war ${DEPLOYMENT_DIR}
