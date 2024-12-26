plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.nvidia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nvidia"
        minSdk = 28
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
    buildFeatures {
        viewBinding = true
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
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Google Play services
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    // Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")  // Correct version
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")  // Correct version

    // OkHttp dependencies
    implementation("com.squareup.okhttp3:okhttp:4.9.3")  // Correct version
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")  // Correct version
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.android.gms:play-services-location:18.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.android.gms:play-services-location:19.0.1")

    // OSMDroid (if required)
    // implementation 'org.osmdroid:osmdroid-android:6.0.0'
}
