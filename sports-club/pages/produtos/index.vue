<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar produtos... Aguarde um momento'
        item-key='tipo'
        expand-icon
        :headers='headers'
        :items='produtos'
        class='elevation-1'
        no-data-text='Não existem produtos'
      >
        <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title>Produtos</v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogEdit" max-width='500px'>
              <edit-produto @close="closeEdit" :key="editarKey" :produto="editedItem" :title="formTitle"></edit-produto>
            </v-dialog>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="criarKey+=1">Criar Produto</v-btn>
              </template>
              <create-produto @close="close" :key="criarKey" :produto="editedItem" :title="formTitle"></create-produto>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">
          <v-icon small class="mr-2" @click="editItem(item)"> {{ icons.mdiPencil }} </v-icon>
          <v-icon small @click="deleteItem(item)"> {{ icons.mdiDelete }} </v-icon>
        </template>
      </v-data-table>
      <v-footer color = "white" style="padding-top: 0px;">
        <v-btn color='primary' dark @click='back'> Voltar </v-btn>
      </v-footer>
    </v-card>
  </v-app>
</template>
<script>
/* eslint-disable */
import { mdiPencil, mdiDelete } from '@mdi/js'
import EditarProduto from './editar'
import CriarProduto from './criar'

export default {
  components: {
    'edit-produto': EditarProduto,
    'create-produto': CriarProduto
  },
  data () {
    return {
      loading: true,
      icons: {
        mdiPencil,
        mdiDelete
      },
      headers: [
        { text: 'Tipo', value: 'tipo', align: 'center', sortable: false },
        { text: 'Descrição', value: 'descrição', align: 'center', sortable: false },
        { text: 'Valor Base', value: 'valorBase', align: 'center', sortable: false },
        { text: 'Actions', value: 'action', sortable: false }
      ],
      produtos: [],
      dialog: false,
      dialogEdit: false,
      editarKey: 0,
      criarKey: 0,
      editedIndex: -1,
      editedItem: {
        tipo: '',
        descrição: '',
        valorBase: 0
      },
      defaultItem: {
        tipo: '',
        descrição: '',
        valorBase: 0
      }
    }
  },
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'Novo Produto' : 'Editar Produto'
    }
  },
  watch: {
    dialog (val) {
      val || this.close()
    },
    dialogEdit (val) {
      val || this.close()
    }
  },
  created () {
    this.getProdutos()
  },
  methods: {
    getProdutos () {
      this.$axios.$get('/api/produtos').then((produtos) => {
        this.produtos = produtos
        this.loading = false
      })
    },
    editItem (item) {
      this.editarKey += 1;
      this.editedIndex = this.produtos.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogEdit = true
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Produto: ${item.tipo}?`) &&
      this.$axios.$delete(`/api/produtos/${item.tipo}`, {})
        .then(() => {
          this.loading = true
          this.getProdutos()
        })
    },
    back () {
      this.$router.push('/')
    },
    close () {
      this.dialog = false
      this.getProdutos()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    closeEdit () {
      this.dialogEdit = false
      this.getProdutos()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
  }
}
</script>
<style>
</style>
