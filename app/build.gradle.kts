plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myfirstapp"
    compileSdk {
        version = release(36)
    }
    // [수정됨] Kotlin DSL 방식의 서명 설정
    signingConfigs {
        create("release") {
            // Kotlin에서는 'def' 대신 'val' 사용
            val keystoreFile = file("release.jks")

            // 환경변수가 있는지 확인 (CI 환경)
            if (System.getenv("ANDROID_KEYSTORE_PASSWORD") != null) {
                storeFile = file("release.jks")
                storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("ANDROID_KEY_ALIAS")
                keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
            } else if (keystoreFile.exists()) {
                // 로컬 환경 (연습용 하드코딩 예시)
                storeFile = file("release.jks")
                storePassword = "PASSWORD_HERE"
                keyAlias = "key0"
                keyPassword = "PASSWORD_HERE"
            }
        }
    }

    defaultConfig {
        applicationId = "com.example.myfirstapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // [추가할 부분 2] 위에서 만든 서명 설정 적용
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}