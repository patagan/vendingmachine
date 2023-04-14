<template>
    <v-container col-sm-6 offset-sm-3>
      <h2>Register</h2>
      <v-form ref="firstForm" v-model="firstValid">
        <v-text-field
          v-model="user.username"
          :rules="usernameRules"
          label="Username"
          required
        ></v-text-field>
      </v-form>
      <v-form ref="secondForm" v-model="secondValid">
        <v-text-field
          v-model="user.password"
          :rules="passwordRules"
          label="Password"
          type="password"
          @change="validate"
          required
        ></v-text-field>
        <v-text-field
          v-model="repeatPassword"
          :rules="passwordRules"
          label="Repeat Password"
          type="password"
          @change="validate"
          required
        ></v-text-field>
        <v-radio-group
          v-model="user.role"
          inline
        >
          <v-radio
            label="Buyer"
            value="BUYER"
          ></v-radio>
          <v-radio
            label="Seller"
            value="SELLER"
          ></v-radio>
        </v-radio-group>
      </v-form>
      <v-btn
        :disabled="!firstValid || !secondValid"
        color="success"
        class="mr-4"
        @click="onRegister"
      >
        Register
      </v-btn>
      <router-link to="/login" class="btn btn-link">Cancel</router-link>
    </v-container>
  </template>
  
  <script>
  import { mapState, mapActions } from "vuex";
  
  export default {
    data() {
      return {
        user: {
          username: "",
          password: "",
          role:"BUYER"
        },
        repeatPassword: "",
        usernameRules: [v => !!v || "Username is required"],
        passwordRules: [
          v => !!v || "Password is required",
          () =>
            this.user.password === this.repeatPassword ||
            this.repeatPassword === this.user.password ||
            "Password must match"
        ],
        submitted: false,
        firstValid: true,
        secondValid: true
      };
    },
    computed: {
        ...mapState({
            registering: state => state.account.registering
        })
    },
    methods: {
      ...mapActions(["register"]),
      onRegister() {
        if (this.firstValid && this.secondValid) {
          this.register(this.user);
        }
      },
      validate() {
        this.$refs.secondForm.validate();
      }
    }
  };
  </script>