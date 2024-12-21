package com.online.store.Utility;

public enum GlobalEndpoints {
    HOME {
        @Override
        public String toString() {
            return "HOME";
        }
    },
    HOME_REDIRECT {
        @Override
        public String toString() {
            return "redirect:/";
        }
    },
    ADD_PRODUCT {
        @Override
        public String toString() {
            return "add-product";
        }
    },
    EDIT_PRODUCT {
        @Override
        public String toString() {
            return "edit-product";
        }
    },
    VIEW_PRODUCT_DETAILS {
        @Override
        public String toString() {
            return "view-product-details";
        }
    },
    LOGIN {
        @Override
        public String toString() {
            return "login";
        }
    },
    LOGIN_FAIL {
        @Override
        public String toString() {
            return "redirect:/login?error";
        }
    },
    REGISTER {
        @Override
        public String toString() {
            return "register";
        }
    },
    REGISTER_FAIL {
        @Override
        public String toString() {
            return "redirect:/register?fail";
        }
    }
}
