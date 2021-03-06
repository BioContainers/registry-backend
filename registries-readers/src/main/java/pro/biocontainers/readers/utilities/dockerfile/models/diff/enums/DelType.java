package pro.biocontainers.readers.utilities.dockerfile.models.diff.enums;


public enum DelType implements ChangeType<DelType>{    
    ADD("ADD"),
    CMD("CMD"),
    COPY("COPY"),
    COMMENT("COMMENT"),
    ENTRYPOINT("ENTRYPOINT"),
    ENV("ENV"),
    EXPOSE("EXPOSE"),
    EXECUTABLE_PARAMETER("EXECUTABLE_PARAMETER"),
    FROM("FROM"),
    LABEL("LABEL"),
    MAINAINER("MAINAINER"),
    ONBUILD("ONBUILD"),
    RUN("RUN"),
    STOPSIGNAL("STOPSIGNAL"),
    USER("USER"),
    VOLUME("VOLUME"),
    WORKDIR("WORKDIR"),
    SOURCE("SOURCE"),
    DESTINATION("DESTINATION"),
    ARG("ARG"),
    EXECUTABLE("EXECUTABLE"),
    PARAMETER("PARAMETER"),
    KEY("KEY"),
    VALUE("VALUE"),
    PORT("PORT"),
    IMAGE("IMAGE"),
    IMAGE_NAME("IMAGE_NAME"),
    IMAGE_VERSION_STRING("IMAGE_VERSION_STRING"),
    IMAGE_VERSION_NUMBER("IMAGE_VERSION_NUMBER"),
    IMAGE_VERSION_DIGEST("IMAGE_VERSION_DIGEST"),
    OPTION_PARAMETER("OPTION_PARAMETER"),
    MAINTAINER("MAINTAINER"),
    SIGNAL("SIGNAL"),
    USER_NAME("USER_NAME"),
    PATH("PATH"),
    HEALTHCHECK("HEALTHCHECK");

    DelType valueOf(){
        return valueOf(name());
    }


    private final String formatted;


    DelType(String formatted){
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return formatted;
    }
}
