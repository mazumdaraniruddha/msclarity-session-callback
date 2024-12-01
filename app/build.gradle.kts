plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.sampleclarityinitialise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sampleclarityinitialise"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.workManager)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.ms.clarity) {
        // Ms clarity also pulls in protobuf java lite dependency, which is used by our grpc stack.
        // We exclude transitive deps here to avoid conflict
        exclude("com.google.protobuf", "protobuf-javalite")
    }

    // Simulate our other grpc dependencies, which causes a transitive conflict with Ms clarity.
    api(libs.grpc.stub)
    api(libs.grpc.android)
    api(libs.grpc.util)
    api(libs.grpc.core)
    api(libs.grpc.okhttp)
    api(libs.grpc.protobuf)
    api(libs.grpc.protobuf.lite) {
        because(
            "grpc-protobuf does include protobuf-javalite at runtime." +
                    "But, not at compile time due to which was getting compile issues. So, added it explicitly."
        )

        // Excluded as there are few classes which are coming from protobuf-java(grpc-protobuf) and protobuf-javalite.
        // Making it compile time error.
        // Ref: https://github.com/grpc/grpc-java/blob/v1.66.x/protobuf/build.gradle#L31
        exclude("com.google.protobuf", "protobuf-javalite")
    }
}