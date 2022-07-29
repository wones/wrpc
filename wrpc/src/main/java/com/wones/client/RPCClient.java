package com.wones.client;

import com.wones.common.RPCRequest;
import com.wones.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
