package com.civicvoice.np.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.UserRole
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun LoginScreen(
    onLogin: (String, UserRole) -> Unit
) {
    var showRoleSelection by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(UserRole.CITIZEN) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Campaign,
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to CivicVoice",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your voice matters in shaping the community",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            if (!showRoleSelection) {
                Button(
                    onClick = { showRoleSelection = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign in with Google")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { showRoleSelection = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continue as Guest")
                }
            } else {
                Text(
                    text = "Select Your Role",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                RoleSelectionCard(
                    role = UserRole.CITIZEN,
                    icon = Icons.Default.Person,
                    title = "Citizen",
                    description = "Share and vote on community suggestions",
                    isSelected = selectedRole == UserRole.CITIZEN,
                    onSelect = { selectedRole = UserRole.CITIZEN }
                )

                Spacer(modifier = Modifier.height(12.dp))

                RoleSelectionCard(
                    role = UserRole.EXPERT,
                    icon = Icons.Default.School,
                    title = "Expert",
                    description = "Provide professional insights and analysis",
                    isSelected = selectedRole == UserRole.EXPERT,
                    onSelect = { selectedRole = UserRole.EXPERT }
                )

                Spacer(modifier = Modifier.height(12.dp))

                RoleSelectionCard(
                    role = UserRole.AUTHORITY,
                    icon = Icons.Default.AdminPanelSettings,
                    title = "Authority",
                    description = "Review and implement community suggestions",
                    isSelected = selectedRole == UserRole.AUTHORITY,
                    onSelect = { selectedRole = UserRole.AUTHORITY }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val name = when (selectedRole) {
                            UserRole.CITIZEN -> "Guest User"
                            UserRole.EXPERT -> "Expert User"
                            UserRole.AUTHORITY -> "Authority User"
                        }
                        onLogin(name, selectedRole)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continue")
                }
            }
        }
    }
}

@Composable
fun RoleSelectionCard(
    role: UserRole,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onSelect
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder().copy(width = 2.dp)
        } else {
            CardDefaults.outlinedCardBorder()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onSelect
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    CivicVoiceTheme {
        LoginScreen(onLogin = { _, _ -> })
    }
}
