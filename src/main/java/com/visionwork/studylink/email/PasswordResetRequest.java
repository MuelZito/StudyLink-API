package com.visionwork.studylink.email;

    public class PasswordResetRequest {
        private String email;
        private String newPassword;

        // Getters e setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
