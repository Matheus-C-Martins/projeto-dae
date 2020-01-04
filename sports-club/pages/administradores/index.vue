<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar administradores... Aguarde um momento'
        item-key='username'
        expand-icon
        :headers='headers'
        :items='administradores'
        class='elevation-1'
        no-data-text='Não existem administradores'
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title> Administradores </v-toolbar-title>
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
              <edit-administrador @close="closeEdit" :key="editarKey" :administrador="editedItem" :title="formTitle"></edit-administrador>
            </v-dialog>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="criarKey+=1">Criar Administrador</v-btn>
              </template>
              <create-administrador @close="close" :key="criarKey" :administrador="editedItem" :title="formTitle"></create-administrador>
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
import EditarAdministrador from './editar'
import CriarAdministrador from './criar'

export default {
  components: {
    'edit-administrador': EditarAdministrador,
    'create-administrador': CriarAdministrador
  },
  data () {
    return {
      loading: true,
      icons: {
        mdiPencil,
        mdiDelete
      },
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false },
        { text: 'Actions', value: 'action', sortable: false, filterable: false }
      ],
      administradores: [],
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
      return this.editedIndex === -1 ? 'Novo Administrador' : 'Editar Administrador'
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
    this.getAdministradores()
  },
  methods: {
    getAdministradores () {
      this.$axios.$get('/api/administradores').then((administradores) => {
        this.administradores = administradores
        this.loading = false
      })
    },
    editItem (item) {
      this.editarKey += 1;
      this.editedIndex = this.administradores.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogEdit = true
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Administrador: ${item.username}?`) &&
      this.$axios.$delete(`/api/administradores/${item.username}`, {})
        .then(() => {
          this.loading = true
          this.getAdministradores()
        })
    },
    back () {
      this.$router.push('/')
    },
    close () {
      this.dialog = false
      this.getAdministradores()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    closeEdit () {
      this.dialogEdit = false
      this.getAdministradores()
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
