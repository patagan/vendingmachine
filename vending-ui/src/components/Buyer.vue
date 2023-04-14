<template>
    <v-container class="fill-height">
      <Drawer/>
        <v-container class="container">
          <div v-for="(item, index) in products" :key="index" class="card">
              <ProductCard :product="item" />
          </div>
        </v-container>
    </v-container>
  </template>
  
  <script >
  import ProductCard from '@/components/ProductCard.vue';
  import Drawer from '@/components/Drawer.vue';
  import { mapState, mapActions } from "vuex";

    export default {
    components: { ProductCard, Drawer },
    computed: {
      ...mapState({
          products: state => state.product.products,
          loggedUser: state => state.account.user
      })
    },
    created() {
      // reset login status
      this.getProducts();
      this.getUser(this.loggedUser.id);
    },
    methods: {
      ...mapActions(['getProducts', 'getUser'])
    }
}
  </script>

<style>
  .container {
    column-count: 2;
    align-items: center;
    
  }
  .card {
    margin-bottom: 3rem;
  }
</style>
  