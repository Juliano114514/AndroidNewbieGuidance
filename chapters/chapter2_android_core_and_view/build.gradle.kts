plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.example.chapter2_core_and_view"
  compileSdk = 36

  defaultConfig {
    minSdk = 21

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(libs.bundles.androidx.base)
  implementation(libs.koin.android)
  implementation(libs.bundles.room)
  ksp(libs.room.compiler)
  testImplementation(libs.junit)
  androidTestImplementation(libs.bundles.android.test)

  implementation(project(":lib-ksp-annotation"))
  ksp(project(":lib-ksp-processor"))
}