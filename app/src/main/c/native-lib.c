//
// Created by User on 07/12/2020.
//

#include <jni.h>
#include <stdlib.h>
#include <time.h>

JNIEXPORT jstring JNICALL
Java_com_example_helloworld_MainActivity_ConcString(JNIEnv *env, jobject this, jstring s)
{
    char *concatenated;
    const jbyte *sx;
    jstring retval;

/* Get the UTF-8 characters that represent our java string */
    sx = (*env)->GetStringUTFChars(env, s, NULL);

/* Concatenate the two strings. */
    concatenated = malloc(strlen("Hello ") + strlen(sx) + 1);
    strcpy(concatenated, "Hello ");
    strcat(concatenated, sx);

/* Create java string from our concatenated C string */
    retval = (*env)->NewStringUTF(env, concatenated);

/* Free the memory in sx */
    (*env)->ReleaseStringUTFChars(env, s, sx);

/* Free the memory in concatenated */
    free(concatenated);

    return retval;
}

JNIEXPORT jstring JNICALL
Java_mobileprogramming_devinwinardi_clevernote_MainActivity_ConcString(JNIEnv *env, jobject this, jstring s)
{
    char *concatenated;
    const jbyte *sx;
    jstring retval;

/* Get the UTF-8 characters that represent our java string */
    sx = (*env)->GetStringUTFChars(env, s, NULL);

/* Concatenate the two strings. */
    concatenated = malloc(strlen("Hello ") + strlen(sx) + 1);
    strcpy(concatenated, "Hello ");
    strcat(concatenated, sx);

/* Create java string from our concatenated C string */
    retval = (*env)->NewStringUTF(env, concatenated);

/* Free the memory in sx */
    (*env)->ReleaseStringUTFChars(env, s, sx);

/* Free the memory in concatenated */
    free(concatenated);

    return retval;
}