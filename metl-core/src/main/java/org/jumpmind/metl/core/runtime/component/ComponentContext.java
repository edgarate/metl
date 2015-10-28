/**
 * Licensed to JumpMind Inc under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU General Public License, version 3.0 (GPLv3)
 * (the "License"); you may not use this file except in compliance
 * with the License.
 *
 * You should have received a copy of the GNU General Public License,
 * version 3.0 (GPLv3) along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jumpmind.metl.core.runtime.component;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jumpmind.metl.core.model.AgentDeployment;
import org.jumpmind.metl.core.model.Flow;
import org.jumpmind.metl.core.model.FlowStep;
import org.jumpmind.metl.core.runtime.IExecutionTracker;
import org.jumpmind.metl.core.runtime.resource.IResourceRuntime;

public class ComponentContext {

    AgentDeployment deployment;

    FlowStep flowStep;

    Flow manipulatedFlow;

    IExecutionTracker executionTracker;

    Map<String, IResourceRuntime> deployedResources;

    Map<String, Serializable> flowParameters;

    Map<String, String> globalSettings;

    ComponentStatistics componentStatistics = new ComponentStatistics();

    public ComponentContext(AgentDeployment deployment, FlowStep flowStep, Flow manipulatedFlow, IExecutionTracker executionTracker,
            Map<String, IResourceRuntime> deployedResources, Map<String, Serializable> flowParameters, Map<String, String> globalSettings) {
        this.deployment = deployment;
        this.flowParameters = Collections.synchronizedMap(flowParameters == null ? new HashMap<>() : new HashMap<>(flowParameters));
        this.flowStep = flowStep;
        this.manipulatedFlow = manipulatedFlow;
        this.executionTracker = executionTracker;
        this.deployedResources = deployedResources;
        this.flowParameters = flowParameters;
        this.globalSettings = globalSettings;
    }

    public AgentDeployment getDeployment() {
        return deployment;
    }

    public FlowStep getFlowStep() {
        return flowStep;
    }

    public Flow getManipulatedFlow() {
        return manipulatedFlow;
    }

    public IExecutionTracker getExecutionTracker() {
        return executionTracker;
    }

    public Map<String, IResourceRuntime> getDeployedResources() {
        return deployedResources;
    }

    public IResourceRuntime getResourceRuntime() {
        return deployedResources.get(flowStep.getComponent().getResourceId());
    }

    public Map<String, Serializable> getFlowParameters() {
        return flowParameters;
    }

    public Map<String, String> getFlowParametersAsString() {
        Map<String, String> params = new HashMap<String, String>();
        for (String key : new HashSet<>(flowParameters.keySet())) {
            Serializable value = flowParameters.get(key);
            if (value != null) {
                params.put(key, value.toString());
            } else {
                params.put(key, null);
            }
        }
        return params;
    }

    public void setComponentStatistics(ComponentStatistics componentStatistics) {
        this.componentStatistics = componentStatistics;
    }

    public ComponentStatistics getComponentStatistics() {
        return componentStatistics;
    }

    public Map<String, String> getGlobalSettings() {
        return globalSettings;
    }

}
