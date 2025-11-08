#!/bin/bash
# Create placeholder launcher icons using ImageMagick convert
# If convert is not available, we'll skip this

if command -v convert &> /dev/null; then
    # Create icons for different densities
    convert -size 48x48 xc:#0057B7 app/src/main/res/mipmap-mdpi/ic_launcher.png
    convert -size 72x72 xc:#0057B7 app/src/main/res/mipmap-hdpi/ic_launcher.png
    convert -size 96x96 xc:#0057B7 app/src/main/res/mipmap-xhdpi/ic_launcher.png
    convert -size 144x144 xc:#0057B7 app/src/main/res/mipmap-xxhdpi/ic_launcher.png
    convert -size 192x192 xc:#0057B7 app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
    
    # Create round icons
    convert -size 48x48 xc:#0057B7 app/src/main/res/mipmap-mdpi/ic_launcher_round.png
    convert -size 72x72 xc:#0057B7 app/src/main/res/mipmap-hdpi/ic_launcher_round.png
    convert -size 96x96 xc:#0057B7 app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
    convert -size 144x144 xc:#0057B7 app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
    convert -size 192x192 xc:#0057B7 app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
    echo "Icons created successfully"
else
    echo "ImageMagick not found. Creating empty placeholder files..."
    # Create empty files as placeholders
    touch app/src/main/res/mipmap-mdpi/ic_launcher.png
    touch app/src/main/res/mipmap-hdpi/ic_launcher.png
    touch app/src/main/res/mipmap-xhdpi/ic_launcher.png
    touch app/src/main/res/mipmap-xxhdpi/ic_launcher.png
    touch app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
    touch app/src/main/res/mipmap-mdpi/ic_launcher_round.png
    touch app/src/main/res/mipmap-hdpi/ic_launcher_round.png
    touch app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
    touch app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
    touch app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
    echo "Placeholder files created. You'll need to add proper icons later."
fi
