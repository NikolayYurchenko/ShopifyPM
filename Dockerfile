FROM adoptopenjdk/openjdk14
VOLUME /tmp
ARG DEPENDENCY=/build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY ${DEPENDENCY}/tokens app/
EXPOSE 50000
ENTRYPOINT ["java", "-cp","app:app/lib/*","com.eliftech.shopify.ShopifyApplication"]