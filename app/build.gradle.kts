plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
        alias(libs.plugins.safeArg)

    id("kotlin-kapt")
}

android {
    namespace = "com.example.dictionary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dictionary"
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //bottom nav
    implementation ("com.google.android.material:material:1.2.0")

    implementation ("com.github.kirich1409:viewbindingpropertydelegate-full:1.5.9")

    //circle image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //elastic views
    implementation ("com.github.skydoves:elasticviews:2.1.0")

//    //tapadoo
//    implementation ("com.github.tapadoo:alerter:7.2.4")
}