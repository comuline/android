# Comuline Android Build Scripts
# Usage: make [target]

.PHONY: help setup clean build test lint install run doctor

# Default target
help:
	@echo "Comuline Android Build System"
	@echo ""
	@echo "Available targets:"
	@echo "  setup         - Set up development environment"
	@echo "  clean         - Clean build artifacts"
	@echo "  build         - Build debug APK"
	@echo "  release       - Build release APK"
	@echo "  test          - Run unit tests"
	@echo "  lint          - Run lint checks"
	@echo "  install       - Install debug APK on device"
	@echo "  run           - Build and run debug app"
	@echo "  doctor        - Check development environment"
	@echo "  deps          - Refresh dependencies"

# Setup development environment
setup:
	@echo "Setting up Android development environment..."
	@if [ ! -f local.properties ]; then \
		echo "Creating local.properties..."; \
		echo "sdk.dir=$${ANDROID_HOME}" > local.properties; \
	fi
	@echo "Running Gradle sync..."
	./gradlew --version
	@echo "Development environment ready!"

# Clean build artifacts
clean:
	@echo "Cleaning build artifacts..."
	./gradlew clean
	rm -rf build/
	rm -rf app/build/
	@echo "Clean completed!"

# Build configurations
build:
	@echo "Building debug APK..."
	./gradlew assembleDebug
	@echo "Debug APK built: app/build/outputs/apk/debug/app-debug.apk"

release:
	@echo "Building release APK..."
	./gradlew assembleRelease
	@echo "Release APK built: app/build/outputs/apk/release/"

# Build bundle for Play Store
bundle:
	@echo "Building release bundle..."
	./gradlew bundleRelease
	@echo "Release bundle built: app/build/outputs/bundle/release/"

# Testing
test:
	@echo "Running unit tests..."
	./gradlew test
	@echo "Test report: app/build/reports/tests/"

test-ui:
	@echo "Running instrumented tests..."
	./gradlew connectedAndroidTest

# Linting
lint:
	@echo "Running lint checks..."
	./gradlew lint
	@echo "Lint report: app/build/reports/lint-results.html"

# Installation
install: build
	@echo "Installing debug APK..."
	adb install -r app/build/outputs/apk/debug/app-debug.apk
	@echo "Debug APK installed!"

uninstall:
	@echo "Uninstalling app..."
	adb uninstall com.comuline.app

# Run the app
run: install
	@echo "Launching Comuline app..."
	adb shell am start -n com.comuline.app/com.comuline.app.ui.MainActivity

# Quick development workflow
dev: clean build run
	@echo "Development build and run completed!"

# Dependencies
deps:
	@echo "Refreshing dependencies..."
	./gradlew --refresh-dependencies
	@echo "Dependencies refreshed!"

# Development helpers
doctor:
	@echo "Checking development environment..."
	@echo ""
	@echo "Java version:"
	@java -version 2>&1 | head -n 1
	@echo ""
	@echo "Gradle version:"
	@./gradlew --version | grep "Gradle" | head -n 1
	@echo ""
	@echo "Kotlin version:"
	@./gradlew --version | grep "Kotlin" | head -n 1 || echo "Kotlin version info not available"
	@echo ""
	@echo "Android SDK:"
	@if [ -z "$${ANDROID_HOME}" ]; then \
		echo "❌ ANDROID_HOME not set"; \
	else \
		echo "✅ ANDROID_HOME: $${ANDROID_HOME}"; \
	fi
	@echo ""
	@echo "ADB devices:"
	@adb devices 2>/dev/null || echo "ADB not found or no devices connected"
	@echo ""

# Project info
info:
	@echo "Project: Comuline Android"
	@echo "Package: com.comuline.app"
	@echo "Min SDK: 23 (Android 6.0)"
	@echo "Target SDK: 34 (Android 14)"
	@echo ""
	@echo "Architecture:"
	@echo "  - MVVM with Jetpack Compose"
	@echo "  - Hilt for dependency injection"
	@echo "  - Room for local database"
	@echo "  - Retrofit for networking"
	@echo ""
	@echo "API Endpoint: https://api.comuline.com"

# Generate signed APK (requires keystore configuration)
sign:
	@echo "Building signed release APK..."
	@echo "Note: Ensure your keystore is configured in gradle.properties"
	./gradlew assembleRelease
	@echo "Signed APK built: app/build/outputs/apk/release/"

# Check for updates
check-updates:
	@echo "Checking for dependency updates..."
	./gradlew dependencyUpdates
	@echo "Update report: build/dependencyUpdates/report.html"