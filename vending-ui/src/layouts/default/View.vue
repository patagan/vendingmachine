<template>
  <v-main>
    <div v-if="message" class="alert" :class="type">{{ message }}</div>
    <router-view />
  </v-main>
</template>

<script>
export default {
  computed: {
    message() {
      return this.$store.state.alert.message;
    },
    type() {
      return this.$store.state.alert.type;
    }
  }
};
</script>

<style>
.alert {
  padding: 0.75rem 1.25rem;
  margin-bottom: 1rem;
  border: 1px solid transparent;
  border-radius: 0.25rem;
}

.success {
  color: #155724;
  background-color: #d4edda;
  border-color: #c3e6cb;
}

.error {
  color: #721c24;
  background-color: #f8d7da;
  border-color: #f5c6cb;
}
</style>






