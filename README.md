# SilOS
Once upon a time, in some dentist center, there was a great need for a wizard. 
A wizard, who could remember all items in a room, 
who knew where each and every product came from,
who could see by a mere barcode what product was meant.

The dentist-folk knew that if they had modern enough Android phones (API >=27, aka android version >= 8.1),
they could summon this wizard into their realm.

## What is nice about this thing
A lot of things, of course!

### Reusable generic base class interface DAO's
This app shows how one could apply reusable generic base class DAO's in Android Room without breaking things (such as LiveData constructs) from Room. This feature comes in handy when, per example, implementing a generic `findByID(long id)`, which you don't want to re-type for every database entity.
This is something not seen anywhere else on the internet.

### Generic Entities, Asynctasks etc

The power of template-hacks in Android Room is shown once more in this app.
Immagine you want to insert a _foo_ entity in your database. You have to write an AsyncTask to do this, since such operations are too large to run on the UI thread. Also, as you probably want to do this from an Activity, and you want to separate database-stuff and UI-stuff for the sake of clean, modularized programming, you need to implement helpers to accept UI input, and translate that to database-related actions.
But what if you had templated entities, taskhandlers, insertion-policies, callback-interfaces, helpers etc.? 

That's right, you only have to make _foo_ extend the right child of DbEntity, and all handlers, policies, interfaces, helpers and everything else you need is defined in generics!

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
This project has been made for Android systems (API >= 27, android version >= 8.1).
This project has been made with the Android Studio IDE.

### Prerequisites
The following is required:

* Android Studio 3 or later
* Anything Android Studio requires
* Android Virtual Device Manager, for VM phones, or a real test phone

### Installing

1. Install Android Studio 3 or later
2. Get all prequisites (Android studio will ask for all of them)
3. In top bar in Android Studio: ```File -> New -> Project from Version Control -> GitHub```

After following these steps, you can start developping.

## Used frameworks
The following frameworks were used:
* transitions framework (animations)
* Android Room (database)
* Firebase Machine Learning Kit (scanning pictures of barcodes)
* Camerakit framework (take picture)

## Authors

* **Sebastiaan Alvarez Rodriguez** - [Sebastiaan Alvarez Rodriguez](https://github.com/Sebastiaan-Alvarez-Rodriguez)
* **Mariska IJpelaar** - [Mariska IJpelaar](https://github.com/MariskaIJpelaar)

## Acknowledgments
* Thanks to [austinkettner](https://github.com/austinkettner) for the creation of the Camerakit framework, as I am truly too lazy to build camera handlers myself
* Thanks to [JetBrains](https://www.jetbrains.com/) for the creation of Android Studio
