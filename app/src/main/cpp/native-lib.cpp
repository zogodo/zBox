#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <jni.h>
#include <string>

#include <android/log.h>
#define  LOG_TAG    "zzz nactive"
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_me_zogodo_goodnight_MainActivity_stringFromJNI(JNIEnv* env, jobject)
{
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
