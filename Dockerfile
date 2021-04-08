FROM adoptopenjdk/openjdk14
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 50000
ENTRYPOINT ["java", "-cp","app:app/lib/*","com.eliftech.shopify.ShopifyApplication"]