<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar atletas... Aguarde um momento'
        item-key='username'
        expand-icon
        :headers='headers'
        :items='atletas'
        class='elevation-1'
        no-data-text='Não existem atletas'
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title> Atletas </v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-text-field
              v-model="search"
              label="Procurar por Username ou Email"
              hide-details
              outlined
              dense
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogEdit" max-width='500px'>
              <edit-atleta @close="closeEdit" :key="editarKey" :atleta="editedItem" :title="formTitle"></edit-atleta>
            </v-dialog>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="criarKey+=1">Criar Atleta</v-btn>
              </template>
              <create-atleta @close="close" :key="criarKey" :atleta="editedItem" :title="formTitle"></create-atleta>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">          
          <v-icon small @click="expandItem(item)"> {{ icons.mdiAccount }} </v-icon>
          <v-icon small @click="editItem(item)"> {{ icons.mdiPencil }} </v-icon>
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
import { mdiPencil, mdiDelete, mdiAccount } from '@mdi/js'
import EditarAtleta from './editar'
import CriarAtleta from './criar'

export default {
  components: {
    'edit-atleta': EditarAtleta,
    'create-atleta': CriarAtleta
  },
  data () {
    return {
      loading: true,
      icons: {
        mdiPencil,
        mdiDelete,
        mdiAccount
      },
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false },
        { text: 'Ações', value: 'action', align: 'center', sortable: false, filterable: false }
      ],
      atletas: [],
      dialog: false,
      dialogEdit: false,
      search: '',
      editarKey: 0,
      criarKey: 0,
      editedIndex: -1,
      editedItem: {
        username: '',
        nome: '',
        email: '',
        password: ''
      },
      defaultItem: {
        username: '',
        nome: '',
        email: '',
        password: ''
      }
    }
  },
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'Novo Atleta' : 'Editar Atleta'
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
    this.getAtletas()
  },
  methods: {
    getAtletas () {
      this.$axios.$get('/api/atletas').then((atletas) => {
        this.atletas = atletas
        this.loading = false
      })
    },
    editItem (item) {
      this.editarKey += 1;
      this.editedIndex = this.atletas.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogEdit = true
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Atleta: ${item.username}?`) &&
      this.$axios.$delete(`/api/atletas/${item.username}`, {}).then(() => {
        this.loading = true
        this.getAtletas()
      })
    },
    expandItem (item) {
      this.$router.push(`/atletas/${item.username}`)
    },
    back () {
      this.$router.push('/')
    },
    close () {
      this.dialog = false
      this.getAtletas()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    closeEdit () {
      this.dialogEdit = false
      this.getAtletas()
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
