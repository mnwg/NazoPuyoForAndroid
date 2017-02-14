#include <jni.h>
#include <string>

JNIEXPORT jintArray JNICALL
Java_com_example_jason_nazocalculator_MainActivity_getNode(JNIEnv *env, jobject instance,
                                                           jintArray main_, jintArray next_) {
    jint *main = env->GetIntArrayElements(main_, NULL);
    jint *next = env->GetIntArrayElements(next_, NULL);
    env->ReleaseIntArrayElements(main_, main, 0);
    env->ReleaseIntArrayElements(next_, next, 0);

    jclass clsj = env->GetObjectClass(instance);
    jfieldID fid = env->GetFieldID(clsj, "MAIN_WIDTH" , "I");
    int MAIN_WIDTH = env->GetIntField(instance, fid );
    fid = env->GetFieldID(clsj, "MAIN_HEIGHT" , "I");
    int MAIN_HEIGHT = env->GetIntField(instance, fid );
    fid = env->GetFieldID(clsj, "NEXT_WIDTH" , "I");
    int NEXT_WIDTH = env->GetIntField(instance, fid );
    fid = env->GetFieldID(clsj, "NEXT_HEIGHT" , "I");
    int NEXT_HEIGHT = env->GetIntField(instance, fid );
}

extern "C"
jstring
Java_com_example_jason_nazocalculator_MainActivity_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
