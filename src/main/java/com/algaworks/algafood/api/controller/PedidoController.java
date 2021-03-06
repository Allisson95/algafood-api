package com.algaworks.algafood.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private FluxoPedidoService fluxoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @GetMapping
    @Override
    public PagedModel<PedidoResumoModel> pesquisar(
            PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pagePedidos = pedidoRepository.findAll(PedidoSpecs.filter(filter), pageable);
        return pagedResourcesAssembler.toModel(pagePedidos, pedidoResumoModelAssembler);
    }

    @GetMapping("/{codigoPedido}")
    @Override
    public PedidoModel buscar(@PathVariable UUID codigoPedido) {
        Pedido pedidoSalvo = emissaoPedido.buscar(codigoPedido);
        return pedidoModelAssembler.toModel(pedidoSalvo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public PedidoModel emitir(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomain(pedidoInput);

            // TODO: Tornar din??mico quando autentica????o for implementada.
            Usuario cliente = new Usuario();
            cliente.setId(1L);
            pedido.setCliente(cliente);

            Pedido pedidoEmitido = emissaoPedido.emitir(pedido);

            return pedidoModelAssembler.toModel(pedidoEmitido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{codigoPedido}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> confirmar(@PathVariable UUID codigoPedido) {
        fluxoPedido.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigoPedido}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> entregar(@PathVariable UUID codigoPedido) {
        fluxoPedido.entregar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigoPedido}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> cancelar(@PathVariable UUID codigoPedido) {
        fluxoPedido.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

}
