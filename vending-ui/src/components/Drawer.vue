<template>
    <v-navigation-drawer
    class="bg-deep-purple"
    theme="dark"
    permanent
    >
    <v-list color="transparent">
        <ProductForm v-if="isSeller" isList="true"/>
        <TopUpDialog v-if="isBuyer"/>
        <v-list-item v-if="isBuyer" prepend-icon="mdi-gavel" title="Reset Saldo" @click="reset"></v-list-item>
    </v-list>

    <template v-slot:append>
        <div class="pa-2">
        <v-btn block @click="doLogout">
            Logout
        </v-btn>
        </div>
    </template>
    </v-navigation-drawer>
</template>

<script >
import ProductForm from '@/components/ProductForm.vue';
import TopUpDialog from '@/components/TopUpDialog.vue';
  import { mapState, mapActions } from "vuex";

    export default {
    data() {
        return {
            items: ["Item 1", "Item 2", "Item 3"]
        };
    },
    computed: {
        ...mapState({
            loggedUser: state => state.account.user
        }),
        isSeller() {
            return this.loggedUser ? this.loggedUser.role === "SELLER":false
        },
        isBuyer() {
            return this.loggedUser ? this.loggedUser.role === "BUYER":false
        }
    },
    components: { ProductForm, TopUpDialog },
    methods: {
      ...mapActions(['addProduct', 'logout', 'resetDeposit']),
      doLogout() {
        this.logout();
      },
      reset() {
        this.resetDeposit();
      }
    }
}

</script>