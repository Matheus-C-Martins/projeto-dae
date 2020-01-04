<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar pagamentos... Aguarde um momento'
        item-key='username,tipoProduto'
        expand-icon
        :headers='headers'
        :items='pagamentos'
        class='elevation-1'
        no-data-text='Não existem pagamentos'
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title>Pagamentos</v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-text-field
              v-model="search"
              label="Procurar por nome"
              hide-details
              outlined
              dense
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogEdit" max-width='500px'>
              <edit-pagamento @close="closeEdit" :key="editarKey" :pagamento="editedItem" :title="formTitle"></edit-pagamento>
            </v-dialog>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="criarKey+=1">Criar Pagamento</v-btn>
              </template>
              <create-pagamento @close="close" :key="criarKey" :pagamento="editedItem" :title="formTitle"></create-pagamento>
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
import EditarPagamento from './editar'
import CriarPagamento from './criar'

export default {
  components: {
    'edit-pagamento': EditarPagamento,
    'create-pagamento': CriarPagamento
  },
  data () {
    return {
      loading: true,
      search: '',
      icons: {
        mdiPencil,
        mdiDelete
      },
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false },
        { text: 'Tipo Produto', value: 'tipoProduto', align: 'center', sortable: false, filterable: false },
        { text: 'Quantidade', value: 'quantidade', align: 'center', sortable: false, filterable: false },
        { text: 'Preço', value: 'preco', align: 'center', sortable: false, filterable: false },
        { text: 'Estado', value: 'estado', align: 'center', sortable: false, filterable: false },
        { text: 'Actions', value: 'action',  align: 'center', sortable: false, filterable: false }
      ],
      pagamentos: [],
      dialog: false,
      dialogEdit: false,
      editarKey: 0,
      criarKey: 0,
      editedIndex: -1,
      editedItem: {
        username: '',
        tipoProduto: '',
        quantidade: 0,
        preco: 0,
        estado: ''
      },
      defaultItem: {
        username: '',
        tipoProduto: '',
        quantidade: 0,
        preco: 0,
        estado: ''
      }
    }
  },
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'Novo Pagamento' : 'Editar Pagamento'
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
    this.getPagamentos()
  },
  methods: {
    getPagamentos () {
      this.$axios.$get('/api/pagamentos').then((pagamentos) => {
        this.pagamentos = pagamentos
        this.loading = false
      })
    },
    editItem (item) {
      this.editarKey += 1;
      this.editedIndex = this.pagamentos.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogEdit = true
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Pagamento: ${item.username} ${item.tipoProduto}?`) &&
      this.$axios.$delete(`/api/produtos/${item.username}&&${item.tipoProduto}`, {})
        .then(() => {
          this.loading = true
          this.getPagamentos()
        })
    },
    back () {
      this.$router.push('/')
    },
    close () {
      this.dialog = false
      this.getPagamentos()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    closeEdit () {
      this.dialogEdit = false
      this.getPagamentos()
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