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
            <v-toolbar-title>Administradores</v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-text-field
              v-model="search"
              label="Procurar por Email"
              hide-details
              outlined
              dense
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogEdit" max-width='500px'>
              <edit-admin @close="closeEdit" :administrador="editedItem" :title="formTitle"></edit-admin>
            </v-dialog>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on'>Criar Administrador</v-btn>
              </template>
              <v-card>
                <v-card-title class="headline"> {{ formTitle }} </v-card-title>
                <v-card-text class="pa-0">
                  <v-container style="padding-bottom: 0px; padding-top: 0px;">
                    <v-row dense>
                      <v-col>
                        <v-text-field
                          v-model='editedItem.username'
                          label='Username'
                          :error-messages="usernameErrors"
                          outlined
                          dense
                          @input="$v.editedItem.username.$touch()"
                          @blur="$v.editedItem.username.$touch()">
                        </v-text-field>
                      </v-col>
                      <v-col>
                        <v-text-field
                        v-model='editedItem.nome'
                        label='Nome'
                        :error-messages="nomeErrors"
                        outlined
                        dense
                        @input="$v.editedItem.nome.$touch()"
                        @blur="$v.editedItem.nome.$touch()">
                        </v-text-field>
                      </v-col>
                    </v-row>
                    <v-row dense>
                      <v-col>
                        <v-text-field
                          v-model="editedItem.email"
                          label='Email Address'
                          :error-messages="emailErrors"
                          outlined
                          dense
                          required
                          @input="$v.editedItem.email.$touch()"
                          @blur="$v.editedItem.email.$touch()"
                        ></v-text-field>
                      </v-col>
                      <v-col>
                        <v-text-field
                          v-model='editedItem.password'
                          :value="editedItem.password"
                          label='Password'
                          :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                          @click:append='() => (value = !value)'
                          :type="value ? 'password' : 'text'"
                          :error-messages="passwordErrors"
                          outlined
                          dense
                          @input="$v.editedItem.password.$touch()"
                          @blur="$v.editedItem.password.$touch()"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions style="padding-top: 0px">
                  <v-spacer></v-spacer>
                  <v-btn color='blue darken-1' text @click='close'>Cancelar</v-btn>
                  <v-btn color='blue darken-1' text @click='save'>Criar</v-btn>
                </v-card-actions>
              </v-card>
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
import { validationMixin } from 'vuelidate'
import { required, maxLength, minLength, email } from 'vuelidate/lib/validators'
import { mdiPencil, mdiDelete } from '@mdi/js'
import EditarAdmin from './editar'

export default {
  /* eslint-disable */
  components: {
    'edit-admin': EditarAdmin
  },
  mixins: [validationMixin],
  validations: {
    editedItem: {
      nome: { required, maxLength: maxLength(25) },
      username: { required, maxLength: maxLength(10) },
      email: { required, email },
      password: { required, minLength: minLength(3) }
    }
  },
  data () {
    return {
      loading: true,
      valid: true,
      value: true,
      search: '',
      icons: {
        mdiPencil,
        mdiDelete
      },
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false, filterable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false },
        { text: 'Actions', value: 'action', sortable: false, filterable: false }
      ],
      administradores: [],
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
      return this.editedIndex === -1 ? 'Novo Administrador' : 'Editar Administrador'
    },
    nomeErrors () {
      const errors = []
      if (!this.$v.editedItem.nome.$dirty) { return errors }
      !this.$v.editedItem.nome.maxLength && errors.push('Name must be at most 25 characters long.')
      !this.$v.editedItem.nome.required && errors.push('Name is required.')
      return errors
    },
    usernameErrors () {
      const errors = []
      if (!this.$v.editedItem.username.$dirty) { return errors }
      !this.$v.editedItem.username.maxLength && errors.push('Username must be at most 10 characters long.')
      !this.$v.editedItem.username.required && errors.push('Username is required.')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.editedItem.password.$dirty) { return errors }
      !this.$v.editedItem.password.minLength && errors.push('Password must have a minimum of 3 characters.')
      !this.$v.editedItem.password.required && errors.push('Password is required.')
      return errors
    },
    emailErrors () {
      const errors = []
      if (!this.$v.editedItem.email.$dirty) { return errors }
      !this.$v.editedItem.email.email && errors.push('Must be valid e-mail.')
      !this.$v.editedItem.email.required && errors.push('E-mail is required.')
      return errors
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
    this.getAdmins()
  },
  methods: {
    getAdmins () {
      this.$axios.$get('/api/administradores').then((administradores) => {
        this.administradores = administradores
        this.loading = false
      })
    },
    editItem (item) {
      this.editedIndex = this.administradores.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogEdit = true
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Administrador: ${item.username}?`) &&
      this.$axios.$delete(`/api/administradores/${item.username}`, {})
        .then(() => {
          this.loading = true
          this.getAdmins()
        })
    },
    back () {
      this.$router.push('/')
    },
    close () {
      this.dialog = false
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    closeEdit () {
      this.dialogEdit = false
      this.getAdmins()
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    save () {
      if (this.editedIndex > -1) {
        Object.assign(this.administradores[this.editedIndex], this.editedItem)
      } else {
        this.$v.$touch()
        if (this.$v.$error) {
          return
        }
        if (this.formTitle === 'Novo Administrador') {
          this.$axios.$post('/api/administradores', {
            username: this.editedItem.username,
            password: this.editedItem.password,
            nome: this.editedItem.nome,
            email: this.editedItem.email
          })
            .then(() => {
              this.loading = true
              this.getAdmins()
            })
        }
      }
      this.close()
    }
  }
}
</script>
<style>
</style>
