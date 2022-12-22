
return require('packer').startup(function(use)
    use 'wbthomason/packer.nvim'
    use {
        'nvim-telescope/telescope.nvim', tag = '0.1.0',
        requires = { {'nvim-lua/plenary.nvim'} }
    }
    use {"ellisonleao/gruvbox.nvim"}

    use {
        'nvim-treesitter/nvim-treesitter',
        run = function()
            local ts_update = require('nvim-treesitter.install').update({with_sync=true})
            ts_update()
        end,
    }

end 
)
