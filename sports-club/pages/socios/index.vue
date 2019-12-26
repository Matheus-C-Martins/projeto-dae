<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar sócios... Aguarde um momento'
        item-key='username'
        expand-icon
        :headers='headers'
        :items='socios'
        class='elevation-1'
        no-data-text='Não existem sócios'
      >
        <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title>Sócios</v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogEdit" max-width='500px'>
              <edit-socio @close="closeEdit" :socio="editedItem" :title="formTitle"></edit-socio>
            </v-dialog>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on'>Criar Sócios</v-btn>
              </template>
              <create-socio @close="close" :socio="editedItem" :title="formTitle"></create-socio>
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
import EditarSocio from './editar'
import CriarSocio from './criar'

export default {
  components: {
    'edit-socio': EditarSocio,
    'create-socio': CriarSocio
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
        { text: 'Nome', value: 'nome', align: 'center', sortable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false },
        { text: 'Actions', value: 'action', sortable: false }
      ],
      socios: [],
      dialog: false,
      dialogEdit: false,
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
      return this.editedIndex === -1 ? 'Novo Sócio' : 'Editar Sócio'
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
    this.getSocios()
  },
  methods: {
    getSocios () {
      this.$axios.$get('/api/socios').then((socios) => {
        this.socios = socios
        this.loading = false
      })
    },
    editItem (item) {
      this.editedIndex = this.socios.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogEdit = true
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Sócio: ${item.username}?`) &&
      this.$axios.$delete(`/api/socios/${item.username}`, {})
        .then(() => {
          this.loading = true
          this.getSocios()
        })
    },
    back () {
      this.$router.push('/')
    },
    close () {
      this.dialog = false
      this.getSocios()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    closeEdit () {
      this.dialogEdit = false
      this.getSocios()
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
