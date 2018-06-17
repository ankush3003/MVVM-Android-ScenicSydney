# Scenic-Sydney-POC
This is a POC application for Google maps related features.

## Overview
This project demonstrates usage of Google maps SDK in Android. A simple two page application:
1. MainActivity (displays Google maps - with list of default and custom markers saved by user sorted by distance)
2. DetailActivity (for editing marker information like name and label).

## Application usage and assumptions
1. By Default user lands on MainActivity. This has two components: Maps screen (in fragment) containing markers and list screen (click/drag from bottom) with all markers information.
2. Dummy data is fetched from 'assets/sample_data.json' file for 5 pre defined locations, this happens only when app is launched for the first time. For subsequnt launches, values are fetched from database. Network fetch can be included later as per requirements.
3. **To Add a custom marker on Map, Long-click where required**. This will open DetailActivity where marker information can be updated and saved. This will persist across app launches.
4. For now, application assumes User's current location at (-33.9045681462, 151.1956366896). Marker list screen sorts data based on distance from this reference point. This location can be updated as per User's current location when required.
5. MVVM architecture with ROOM library is used for flexibility and persistence.

## References:
1. Butterknife https://github.com/JakeWharton/butterknife
2. Google Maps https://developers.google.com/maps/documentation/android-sdk/intro
3. Sliding up panel https://github.com/umano/AndroidSlidingUpPanel
4. Room Persistence library https://developer.android.com/topic/libraries/architecture/room
