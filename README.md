# movies_mobile_app

👥 Membres du Groupe

    André Kanmegne

    Louis-Carlos Francisco

    Wahab Mounirou

📺 Projet TV Show
🎯 Objectif

Créer une application Android utilisant Jetpack Compose pour lister les émissions TV populaires, effectuer une recherche, et afficher les détails d'une émission sélectionnée.
Architecture basée sur MVVM et Clean Architecture.
🛠️ Technologies Utilisées

    Jetpack Compose : Interface utilisateur moderne et déclarative.

    MVVM (Model-View-ViewModel) : Séparation claire des responsabilités.

    Clean Architecture : Code modulaire et maintenable.

    Retrofit : Appels réseau.

    Dagger-Hilt : Injection de dépendances.

    Coil : Chargement des images.

⚙️ Plugins Gradle
settings.gradle.kts

plugins {
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

build.gradle.kts (niveau module)

plugins {
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

📦 Dépendances

 dependencies 
 {
  
   // Architecture Components
    implementation("androidx.arch.core:core-common:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common:2.8.7")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Coroutine
    val coroutine_version = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.11.0")

    // Coil (Image loading)
    implementation("io.coil-kt.coil3:coil-compose:3.0.3")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.3")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")
}

🧱 Architecture du Projet
🗂️ Data Layer

    Modèles Réseau : Classes mappant les réponses des API.

    Service API : Interface Retrofit définissant les endpoints.

    Repository Implémentation : Implémente l'accès aux données.

🔄 Domain Layer

    Modèles Métiers : Objets simplifiés pour la logique métier.

    Use Cases : Encapsulent la logique métier et les appels au repository.

🎨 Presentation Layer

    ViewModels : Gèrent les états de l’interface utilisateur.

    UI (Jetpack Compose) : Composants visuels.

🌐 Endpoints

    📃 Liste des émissions populaires

    GET /most-popular?page=:page
    Exemple : https://www.episodate.com/api/most-popular?page=1
    🔍 Recherche d'émissions (Search)

    Permet la recherche d’émissions par nom.
    Utilise un algorithme flou pour tolérer les fautes de frappe.
    Résultats triés par nom avec infos de base.

    GET /search?q=:search&page=:page
    Exemple : https://www.episodate.com/api/search?q=arrow&page=1
    📝 Détails d'une émission

    GET /show-details?q=:show
    Exemple : https://www.episodate.com/api/show-details?q=game-of-thrones

✨ Fonctionnalités
📄 Activity 1 : Liste des TV Shows

    Affiche une liste paginée des émissions populaires (page 1).

    Utilise LazyColumn ou LazyVerticalGrid.

    Clic sur un item → ouvre l’activité de détails.

📄 Activity 2 : Détails d’un TV Show

    Affiche l’image, le titre et la description de l’émission sélectionnée.

🔍 Activity 3 : Recherche d’émissions (Search)

    Permet de rechercher des émissions via un champ de texte.

    Affiche les résultats sous forme de liste défilante.

    Gère les erreurs de saisie et les résultats vides.

    Clic sur un item → ouvre l’activité de détails.

🧩 Étapes de Mise en Œuvre
🔧 Configuration du projet(dans most_popular;show_details et search_movies)

    Ajouter les dépendances nécessaires.

📥 Couche Data

    Créer l'interface Retrofit.

    Définir les DTO pour chaque réponse API.

    Implémenter le Repository.

🔁 Couche Domain

    Définir les modèles métiers.

    Création des  Use Cases (Populaires, Recherche, Détails) respectivement dans most_popular;show_details et search_movies

🎨 Couche Presentation

    Création  des ViewModels.

    UI avec Jetpack Compose :

        LazyColumn pour les listes.

        TextField pour la recherche.

        Coil pour les images.

        Column pour les détails.

🧪 Injection avec Dagger-Hilt

    Modules pour les Use Cases, Retrofit et Repositories.

✅ Étapes Finales

    Gestion de la navigation entre les 3 activités.

    Ajout d'un écran de chargement ou ProgressIndicator.

    Gestion  des erreurs réseau (toasts, UI dédiée...).

    Test de la recherche avec des termes mal orthographiés.

    Optimisation la pagination et l'expérience utilisateur.
