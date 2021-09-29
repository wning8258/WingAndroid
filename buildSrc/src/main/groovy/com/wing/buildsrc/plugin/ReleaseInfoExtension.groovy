package com.wing.buildsrc.plugin

class ReleaseInfoExtension {
    String versionCode
    String versionName
    String versionInfo
    String fileName

    ReleaseInfoExtension() {
    }


    @Override
    public String toString() {
        return "ReleaseInfoExtension{" +
                "versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionInfo='" + versionInfo + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}