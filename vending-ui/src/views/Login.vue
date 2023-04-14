<template>
    <v-container col-sm-6 offset-sm-3>
      <h2>Login</h2>
      <v-form ref="form" v-model="valid">
        <v-text-field
          v-model="username"
          :rules="UsernameRules"
          label="Username"
          required
        ></v-text-field>
        <v-text-field
          v-model="password"
          type="password"
          :rules="PasswordRules"
          label="Password"
          required
        ></v-text-field>
        <v-btn :disabled="!valid" color="success" class="mr-4" @click="onSubmit">
          Login
        </v-btn>
        <router-link to="/register" class="btn btn-link">Register</router-link>
      </v-form>
    </v-container>
  </template>
  
  <script>
  import { mapState, mapActions } from "vuex";
  
  export default {
    data() {
      return {
        username: "",
        password: "",
        submitted: false,
        UsernameRules: [v => !!v || "Username is required"],
        PasswordRules: [v => !!v || "Password is required"],
        valid: true
      };
    },
    computed: {
      ...mapState({
        loggingIn: state => state.account.loggedIn,
        user: state => state.account.user
      })
    },
    created() {
      // reset login status
      this.logout();
    },
    methods: {
      ...mapActions(['login', 'logout']),
      onSubmit() {
        const { valid, username, password } = this;
        if (valid && username && password) {
          this.login({ username, password });
        }
      }
    }
  };
  </script>