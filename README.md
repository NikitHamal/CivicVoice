# CivicVoice 🏛️

**A modern civic engagement platform for community-driven change**

CivicVoice is a beautifully designed Android application that empowers citizens to share ideas, vote on community suggestions, and track the progress of civic initiatives. Built with Kotlin and Jetpack Compose using Material 3 design principles.

## ✨ Features

### For Citizens
- 📝 **Create Suggestions** - Share ideas to improve your community
- 👍 **Vote on Proposals** - Support suggestions you believe in
- 💬 **Engage in Discussion** - Comment and collaborate with others
- 📊 **Track Progress** - See real-time updates on suggestion status
- 🔔 **Stay Informed** - Get notifications about community initiatives

### For Authorities
- 📋 **Review Dashboard** - Manage and prioritize community suggestions
- ✅ **Update Status** - Mark progress from Open → Under Review → Implemented
- 📈 **Analytics** - View statistics on categories and sentiment
- 🔥 **AI Priority** - See AI-flagged high-impact suggestions

### Design Highlights
- 🎨 Material 3 design system with dynamic theming
- 🌓 Dark mode support
- 🎯 Clean, intuitive user interface
- 📱 Responsive layout for phones and tablets
- ✨ Smooth animations and transitions
- 🔤 Custom Poppins font family

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Design System**: Material 3
- **Navigation**: Jetpack Navigation Compose
- **State Management**: Kotlin Flows & StateFlow
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)

## 📦 Dependencies

- Jetpack Compose BOM 2024.12.01
- Material 3 with Extended Icons
- Navigation Compose 2.8.5
- Lifecycle & ViewModel Compose 2.8.7
- DataStore Preferences 1.1.1
- Core SplashScreen 1.0.1

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- JDK 17
- Android SDK 35

### Clone and Build

```bash
git clone https://github.com/yourusername/civicvoice.git
cd civicvoice
./gradlew assembleDebug
```

### Run on Emulator/Device

```bash
./gradlew installDebug
```

Or open the project in Android Studio and click Run ▶️

## 📱 App Structure

```
com.civicvoice.np/
├── data/
│   ├── User.kt              # User data model
│   ├── Suggestion.kt        # Suggestion data model
│   ├── Comment.kt           # Comment data model
│   └── MockRepository.kt    # Mock data repository
├── viewmodel/
│   └── MainViewModel.kt     # Main ViewModel
├── ui/
│   ├── theme/              # Theme, colors, typography
│   ├── screens/            # All app screens
│   │   ├── SplashScreen.kt
│   │   ├── OnboardingScreen.kt
│   │   ├── LoginScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── SuggestionDetailScreen.kt
│   │   ├── CreateSuggestionScreen.kt
│   │   ├── ProfileScreen.kt
│   │   └── DashboardScreen.kt
│   └── components/         # Reusable UI components
│       └── SuggestionCard.kt
├── navigation/
│   ├── Screen.kt           # Navigation routes
│   └── AppNavigation.kt    # Navigation setup
└── MainActivity.kt         # App entry point
```

## 🎯 Key Screens

1. **Splash Screen** - Animated logo with smooth transitions
2. **Onboarding** - 3-slide introduction to app features
3. **Login** - Role selection (Citizen/Expert/Authority)
4. **Home** - Feed with tabs (All/Trending/Nearby/Category)
5. **Suggestion Detail** - Full view with AI summary, voting, comments
6. **Create Suggestion** - Form with AI-powered clarity improvements
7. **Profile** - User info, suggestions, settings
8. **Dashboard** - Authority view for managing suggestions

## 🎨 Design System

### Color Palette
- **Primary**: Civic Blue (#0057B7)
- **Secondary**: Civic Green (#00A859)
- **Background**: Light Gray (#F8F9FA)
- **Error**: Red (#D32F2F)

### Typography
All text uses the **Poppins** font family:
- Light (300)
- Regular (400)
- Medium (500)
- SemiBold (600)
- Bold (700)

## 🏗️ Build & Deploy

### GitHub Actions CI/CD

This project includes automated build workflows:

```yaml
# Triggers on push to main branches (excluding wip/**)
# Builds signed release APK
# Uploads artifacts with commit hash
```

**APK Output**: `civicvoice-release-{commit-hash}.apk`

### Manual Build

```bash
# Debug build
./gradlew assembleDebug

# Release build (requires keystore)
./gradlew assembleRelease
```

### Keystore Configuration

The release build is configured to use a public keystore for open-source distribution:

```
Store: keystore/civicvoice-release.keystore
Alias: civicvoice
Password: civicvoice123
```

⚠️ **Note**: This is for demo/testing only. For production, use a secure keystore!

## 📝 Mock Data

The app currently uses mock data stored in `MockRepository.kt`. All interactions (voting, commenting, posting) update local state only. Future versions will integrate with a real backend.

### Sample Categories
- Infrastructure 🏗️
- Education 📚
- Health 🏥
- Environment 🌱
- Transportation 🚌
- Safety 🛡️

## 🔮 Future Enhancements

- [ ] Real backend API integration
- [ ] User authentication (OAuth, Firebase)
- [ ] Push notifications
- [ ] Location-based filtering
- [ ] Image attachments
- [ ] Advanced AI suggestions
- [ ] Multi-language support
- [ ] Accessibility improvements

## 🤝 Contributing

This is an open-source project! Contributions are welcome:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see LICENSE file for details.

## 👥 Authors

Built with ❤️ by the CivicVoice team

## 🙏 Acknowledgments

- Material 3 Design Guidelines
- Jetpack Compose Community
- Google Fonts (Poppins)

---

**Made in Nepal 🇳🇵**