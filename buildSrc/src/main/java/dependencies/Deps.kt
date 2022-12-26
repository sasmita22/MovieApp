package dependencies

object Deps {
    object AndroidX {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.5.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val fragment = "androidx.fragment:fragment-ktx:1.5.5"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    }
    object Material {
        const val core = "com.google.android.material:material:1.7.0"
    }
    object Hilt {
        const val core = "com.google.dagger:hilt-android:2.44"
        const val compiler = "com.google.dagger:hilt-android-compiler:2.44"
    }
    object Youtube {
        const val core = "com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.26"
    }
    object CircleImageView {
        const val core = "de.hdodenhof:circleimageview:3.1.0"
    }
    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:2.9.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
    }
    object Paging3 {
        private const val paging_version = "3.1.1"
        const val runtime = "androidx.paging:paging-runtime-ktx:$paging_version"
        const val common = "androidx.paging:paging-common-ktx:$paging_version"
    }
    object Chucker {
        const val core = "com.github.chuckerteam.chucker:library:3.5.2"
        const val noOp = "com.github.chuckerteam.chucker:library-no-op:3.5.2"
    }
    object Glide {
        const val core = "com.github.bumptech.glide:glide:4.13.2"
        const val compiler = "com.github.bumptech.glide:compiler:4.13.2"
    }
}
